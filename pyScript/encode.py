import os
import pickle
from deepface import DeepFace
import cv2

def preprocess_image(image_path):
    print(f"Preprocessing image: {image_path}")
    # Load the image
    image = cv2.imread(image_path)
    return image

def encode_and_save_images(image_dir, output_file):
    all_images_data = {}
    count = 0

    for filename in os.listdir(image_dir):
        if filename.endswith(".jpg") or filename.endswith(".png") or filename.endswith(".jpeg"):
            print(f"Processing file: {filename}")
            image_path = os.path.join(image_dir, filename)
            preprocessed_image = preprocess_image(image_path)

            try:
                # Get embedding using DeepFace with enforce_detection set to True
                embedding = DeepFace.represent(preprocessed_image, model_name="VGG-Face")[0]["embedding"]
                base_filename = os.path.splitext(filename)[0]
                all_images_data[base_filename] = embedding
                count += 1
            except ValueError as e:
                print(f"Skipping file {image_path}: {e}")

    # Save all preprocessed images data to a single .pkl file
    with open(output_file, 'wb') as pkl_file:
        pickle.dump(all_images_data, pkl_file)

    print(f"Encoding and saving process completed. Total images encoded: {count}")

if __name__ == "__main__":
    image_directory = "img/"
    output_file = "VGG-Face.pkl"
    encode_and_save_images(image_directory, output_file)

