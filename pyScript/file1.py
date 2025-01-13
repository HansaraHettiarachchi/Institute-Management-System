import sys
import pickle
import numpy as np
from deepface import DeepFace
import cv2

import logging
logging.captureWarnings(True)
logging.getLogger('tensorflow').setLevel(logging.ERROR)

def load_image_data(pkl_path):
    with open(pkl_path, 'rb') as pkl_file:
        data = pickle.load(pkl_file)
    return data

def preprocess_image(image_path):
    
    image = cv2.imread(image_path)
    return image

def find_exact_match(new_embedding, image_data, threshold=8):
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

if __name__ == "__main__":
   
    image_data_path = sys.argv[2]
    image_data = load_image_data(image_data_path)

    new_image_path = sys.argv[1]
    preprocessed_new_image = preprocess_image(new_image_path)

    new_embedding = DeepFace.represent(preprocessed_new_image, model_name="Facenet")[0]["embedding"]

    match_name, min_distance = find_exact_match(new_embedding, image_data)
    if match_name:
        print(f"Match found: {match_name} with distance: {min_distance}")
    else:
        print("Person not found.")

