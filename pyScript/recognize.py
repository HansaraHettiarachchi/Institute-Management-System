import os
import pickle
import numpy as np
from deepface import DeepFace
import cv2

class Recognize:

    def __init__(self):
        self.image_data_path = "VGG-Face.pkl"
        self.image_data = self.load_image_data(self.image_data_path)
        self.model = DeepFace.build_model("VGG-Face")
        print("DeepFace model loaded and image data loaded.")

    def load_image_data(self, pkl_path):
        with open(pkl_path, 'rb') as pkl_file:
            data = pickle.load(pkl_file)
        return data

    def find_exact_match(self, new_embedding, image_data, threshold=20.7):
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

    def setData(self, frame):
        new_embedding = DeepFace.represent(frame, model_name="VGG-Face",enforce_detection=False)[0]["embedding"]
        
        match_name, min_distance = self.find_exact_match(new_embedding, self.image_data)
        if match_name:
            return f"Match found: {match_name} with distance: {min_distance}"
        else:
            return "Person not found."

if __name__ == "__main__":
    # Example usage
    recognize = Recognize()
    frame = cv2.imread("images/12.jpg")  # Provide the path to the image you want to process
    results = recognize.setData(frame)
    for result in results:
        print(result)