import os
import pickle
import cv2
from deepface import DeepFace
from mtcnn import MTCNN

def is_clear_image(image):
    """Check if the image is clear using Laplacian variance."""
    gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    laplacian_var = cv2.Laplacian(gray_image, cv2.CV_64F).var()
    return laplacian_var > 50  # Adjust threshold as needed

def preprocess_image(image_path):
    """Preprocess the image for face detection and cropping using MTCNN."""
    print(f"Preprocessing image: {image_path}")
    
    # Load the image
    image = cv2.imread(image_path)
    if image is None:
        print(f"Failed to load image: {image_path}")
        return None

    # Check if the image is clear
    if not is_clear_image(image):
        print(f"Image is not clear: {image_path}")
        return None

    # Initialize MTCNN face detector
    face_detector = MTCNN()

    # Detect faces in the image
    faces = face_detector.detect_faces(image)

    if len(faces) == 0:
        print(f"No face detected in image: {image_path}")
        return None

    # Process each detected face
    for face in faces:
        x, y, w, h = face['box']
        confidence = face['confidence']

        # Filter out low-confidence detections
        if confidence < 0.9:
            continue

        # Crop the face region
        cropped_face = image[y:y+h, x:x+w]

        # Save cropped face image
        preprocessed_image_path = os.path.join("pre_processed_image", os.path.basename(image_path))
        cv2.imwrite(preprocessed_image_path, cropped_face)
        return cropped_face

    print(f"No suitable face detected in image: {image_path}")
    return None

def encode_and_save_images(image_dir, output_file):
    """Encode images and save their embeddings to a pickle file."""
    all_images_data = {}
    count = 0

    # Create the output directory if it doesn't exist
    os.makedirs("pre_processed_image", exist_ok=True)

    for filename in os.listdir(image_dir):
        if filename.lower().endswith((".jpg", ".png", ".jpeg")):
            print(f"Processing file: {filename}")
            image_path = os.path.join(image_dir, filename)
            preprocessed_image = preprocess_image(image_path)

            if preprocessed_image is None:
                print(f"Skipping file {image_path}")
                continue

            try:
                # Get embedding using DeepFace
                embedding = DeepFace.represent(preprocessed_image, model_name="VGG-Face")[0]["embedding"]
                base_filename = os.path.splitext(filename)[0]
                all_images_data[base_filename] = embedding
                count += 1
            except Exception as e:
                print(f"Skipping file {image_path}: {e}")

    # Save all embeddings to a pickle file
    with open(output_file, 'wb') as pkl_file:
        pickle.dump(all_images_data, pkl_file)

    print(f"Encoding and saving process completed. Total images encoded: {count}")

if __name__ == "__main__":
    image_directory = "img/"  # Directory containing images
    output_file = "VGG-Face.pkl"  # Output file for embeddings
    encode_and_save_images(image_directory, output_file)