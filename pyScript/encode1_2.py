import os
import pickle
import cv2
import numpy as np
from keras._tf_keras.keras.models import load_model
from keras._tf_keras.keras.applications.efficientnet_v2 import preprocess_input

class ImageProcessor:
    def __init__(self, model_path, output_dir="pre_processed_image"):
        self.model = load_model(model_path)
        self.face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')
        self.eye_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_eye.xml')
        self.output_dir = output_dir
        os.makedirs(self.output_dir, exist_ok=True)

    def is_image_clear(self, image):
        """Check if the image is clear based on the variance of the Laplacian."""
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        variance_of_laplacian = cv2.Laplacian(gray, cv2.CV_64F).var()
        return variance_of_laplacian > 50  # Adjust the threshold as needed

    def preprocess_image(self, image_path):
        print(f"Preprocessing image: {image_path}")
        # Load the image
        image = cv2.imread(image_path)

        # Check if the image is clear
        if not self.is_image_clear(image):
            print("Image is not clear. Skipping.")
            return None
        
        # Convert to grayscale for face detection
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        
        # Detect faces
        faces = self.face_cascade.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30), flags=cv2.CASCADE_SCALE_IMAGE)
        
        # If no face is detected, skip the image
        if len(faces) == 0:
            print("No human front face detected. Skipping.")
            return None
        else:
            # Process each detected face and select the largest one
            max_area = 0
            largest_face = None
            for (x, y, w, h) in faces:
                roi_gray = gray[y:y+h, x:x+w]
                eyes = self.eye_cascade.detectMultiScale(roi_gray)
                if len(eyes) >= 2:  # Confirm that eyes are detected within the face region
                    area = w * h
                    if area > max_area:
                        max_area = area
                        largest_face = (x, y, w, h)

            if largest_face is None:
                print("No clear face with eyes detected. Skipping.")
                return None

            x, y, w, h = largest_face
            face_img = image[y:y+h, x:x+w]
            print("Human front face with eyes detected and cropped.")

        # Resize the face image to the input size expected by the model (480x480)
        face_img_resized = cv2.resize(face_img, (480, 480))

        # Save the preprocessed image
        base_filename = os.path.basename(image_path)
        save_path = os.path.join(self.output_dir, base_filename)
        cv2.imwrite(save_path, face_img_resized)

        # Preprocess the image for the model
        face_img_resized = preprocess_input(face_img_resized)
        # Add a batch dimension
        face_img_resized = np.expand_dims(face_img_resized, axis=0)
        return face_img_resized

    def encode_and_save_images(self, image_dir, output_file):
        all_images_data = {}
        count = 0

        for filename in os.listdir(image_dir):
            if filename.endswith(".jpg") or filename.endswith(".png") or filename.endswith(".jpeg"):
                print(f"Processing file: {filename}")
                image_path = os.path.join(image_dir, filename)
                preprocessed_image = self.preprocess_image(image_path)

                if preprocessed_image is None:
                    continue

                try:
                    # Get embedding using the loaded model
                    embedding = self.model.predict(preprocessed_image)[0]
                    base_filename = os.path.splitext(filename)[0]
                    all_images_data[base_filename] = embedding.tolist()
                    count += 1
                except Exception as e:
                    print(f"Skipping file {image_path}: {e}")

        # Save all preprocessed images data to a single .pkl file
        with open(output_file, 'wb') as pkl_file:
            pickle.dump(all_images_data, pkl_file)

        print(f"Encoding and saving process completed. Total images encoded: {count}")

if __name__ == "__main__":
    image_directory = "img/"
    output_file = "efficientnetv2l_embeddings.pkl"
    model_path = "efficientnetv2l_model.h5"  # Replace with the actual path to your .h5 file

    processor = ImageProcessor(model_path)
    processor.encode_and_save_images(image_directory, output_file)
