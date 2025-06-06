import os
import pickle
import numpy as np
import cv2
import uuid
from keras._tf_keras.keras.models import load_model
from keras._tf_keras.keras.applications.efficientnet_v2 import preprocess_input

class FaceRecognizer:
    def __init__(self, model_path, image_data_path):
        self.model = load_model(model_path)
        self.image_data = self.load_image_data(image_data_path)
        self.face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')
        self.eye_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_eye.xml')
        self.detected_faces_dir = "detected_faces"
        os.makedirs(self.detected_faces_dir, exist_ok=True)
        print("Keras model and image data loaded.")

    def load_image_data(self, pkl_path):
        with open(pkl_path, 'rb') as pkl_file:
            data = pickle.load(pkl_file)
        return data

    def is_image_clear(self, image):
        """Check if the image is clear based on the variance of the Laplacian."""
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        variance_of_laplacian = cv2.Laplacian(gray, cv2.CV_64F).var()
        return variance_of_laplacian > 2  # Adjust the threshold as needed

    def preprocess_image(self, frame):
        # Check if the image is clear
        if not self.is_image_clear(frame):
            print("Image is not clear. Skipping.")
            return None
        
        # Convert to grayscale for face detection
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        
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
            face_img = frame[y:y+h, x:x+w]
            print("Human front face with eyes detected and cropped.")

        # Save the cropped face image to the detected_faces directory with a random name
        random_filename = f"{uuid.uuid4()}.jpg"
        detected_face_path = os.path.join(self.detected_faces_dir, random_filename)
        cv2.imwrite(detected_face_path, face_img)

        # Resize the face image to the input size expected by the model (480x480)
        face_img_resized = cv2.resize(face_img, (480, 480))

        # Preprocess the image for the model
        face_img_resized = preprocess_input(face_img_resized)
        # Add a batch dimension
        face_img_resized = np.expand_dims(face_img_resized, axis=0)
        return face_img_resized

    def get_embedding(self, preprocessed_image):
        """Get the embedding of the preprocessed image using the model."""
        embedding = self.model.predict(preprocessed_image)[0]
        return embedding

    def find_exact_match(self, new_embedding, threshold=20.0):
        min_distance = float('inf')
        match_name = None

        new_embedding = np.array(new_embedding)
        
        for name, embedding in self.image_data.items():
            embedding = np.array(embedding)
            distance = np.linalg.norm(new_embedding - embedding)
            if distance < min_distance:
                min_distance = distance
                match_name = name

        if min_distance < threshold:
            return match_name, min_distance
        else:
            return None, min_distance

    def recognize(self, frame):
        preprocessed_image = self.preprocess_image(frame)
        if preprocessed_image is None:
            return "Image is not clear or no human front face detected."
        
        new_embedding = self.get_embedding(preprocessed_image)
        match_name, min_distance = self.find_exact_match(new_embedding)
        if match_name:
            return f"Match found: {match_name} with distance: {min_distance}"
        else:
            return "Person not found."

# Usage example
if __name__ == "__main__":
    model_path = "efficientnetv2l_model.h5"  # Replace with the actual path to your .h5 file
    image_data_path = "efficientnetv2l_embeddings.pkl"  # Replace with the actual path to your pickle file

    recognizer = FaceRecognizer(model_path, image_data_path)
    cap = cv2.VideoCapture(0)  # Use camera number 2

    if not cap.isOpened():
        print("Error: Could not open camera.")
    else:
        while True:
            ret, frame = cap.read()
            if not ret:
                print("Failed to capture image.")
                break
            
            result = recognizer.recognize(frame)
            print(result)
            
            # Display the resulting frame
            cv2.imshow('Frame', frame)

            # Press 'q' to quit
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break

        # Release the capture and close windows
        cap.release()
        cv2.destroyAllWindows()
