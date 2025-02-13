import os
import pickle
import numpy as np
from deepface import DeepFace
import cv2
from mtcnn import MTCNN  # Standalone MTCNN library
import random
import string

class Recognize:

    def __init__(self):
        self.image_data_path = "pyScript/VGG-Face.pkl"
        # self.image_data_path = "VGG-Face.pkl"
        self.image_data = self.load_image_data(self.image_data_path)
        self.model = DeepFace.build_model("VGG-Face")
        self.detected_faces_dir = "pyScript/detected_faces"
        os.makedirs(self.detected_faces_dir, exist_ok=True)
        self.face_detector = MTCNN()  # Initialize MTCNN detector
        print("DeepFace model loaded and image data loaded.",flush=True)

    def load_image_data(self, pkl_path):
        with open(pkl_path, 'rb') as pkl_file:
            data = pickle.load(pkl_file)
        return data

    def find_exact_match(self, new_embedding, image_data, threshold=0.90):
        min_distance = float('inf')
        match_name = None

        new_embedding = np.array(new_embedding)
        
        for name, embedding in image_data.items():
            embedding = np.array(embedding)
            distance = np.linalg.norm(new_embedding - embedding)
            if distance < min_distance:
                min_distance = distance
                match_name = name

        if min_distance < threshold:
            return match_name, min_distance
        else:
            return None, min_distance

    @staticmethod
    def is_clear_image(image):
        gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        laplacian_var = cv2.Laplacian(gray_image, cv2.CV_64F).var()
        # print (laplacian_var)
        return laplacian_var > 0  # Adjust threshold as needed

    @staticmethod
    def get_random_string(length=10):
        letters_and_digits = string.ascii_lowercase + string.digits
        return ''.join(random.choice(letters_and_digits) for i in range(length))

    def preprocess_image(self, image):
        """Preprocess the image for face detection and recognition."""
        if not self.is_clear_image(image):
            print("Image is not clear.",flush=True)
            return None

        # Detect faces using MTCNN
        faces = self.face_detector.detect_faces(image)

        if len(faces) == 0:
            print("No face detected in image.",flush=True)
            return None
        
        cropped_faces = []
        for face in faces:
            x, y, w, h = face['box']
            confidence = face['confidence']

            if confidence < 0.9:  # Filter out low-confidence detections
                continue

            # Crop the face region
            cropped_face = image[y:y+h, x:x+w]

            # Save cropped face image with a random name
            random_name = self.get_random_string() + ".jpg"
            preprocessed_image_path = os.path.join(self.detected_faces_dir, random_name)
            cv2.imwrite(preprocessed_image_path, cropped_face)
            cropped_faces.append(cropped_face)

        if len(cropped_faces) == 0:
            print("No suitable face detected in image.",flush=True)
            return None

        return cropped_faces

    def setData(self, frame):
        """
        Process a frame (image) to recognize faces.
        """
        # Preprocess the image (check clarity and detect faces)
        cropped_faces = self.preprocess_image(frame)
        if cropped_faces is None:
            # print ("No suitable face detected in frame.",flush=True)
            return None

        # Recognize faces in the cropped images
        results = []
        for face in cropped_faces:
            new_embedding = DeepFace.represent(face, model_name="VGG-Face", enforce_detection=False)[0]["embedding"]
            match_name, min_distance = self.find_exact_match(new_embedding, self.image_data)

            if match_name:
                print(f"Match found- {match_name} with distance: {min_distance}", flush=True)
                # results.append(f"Match found: {match_name} with distance: {min_distance}")
            else:
                print("No person found", flush=True)
                # results.append("No person found")

        return results
    
# if __name__ == "__main__":
#     # Example usage
#     recognize = Recognize()
#     image_path = "images/14.jpg"  # Provide the path to the image you want to process

#     # Process the image (skips if blurry or no face detected)
#     frame = cv2.imread(image_path)
#     results = recognize.setData(frame)
#     for result in results:
#         print(result)
