import cv2
import numpy as np
from deepface import DeepFace

def is_blurred(image, threshold=100.0):
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    variance_of_laplacian = cv2.Laplacian(gray, cv2.CV_64F).var()
    return variance_of_laplacian < threshold

def detect_and_align_faces(image):
    face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30))
    
    aligned_faces = []
    for (x, y, w, h) in faces:
        face = image[y:y + h, x:x + w]
        aligned_faces.append(face)
    return aligned_faces

def is_front_face_and_clear(face):
    analysis = DeepFace.analyze(img_path=face, actions=['age', 'gender', 'emotion'], enforce_detection=False)
    dominant_emotion = analysis[0]['dominant_emotion']
    if dominant_emotion == 'neutral' and not is_blurred(face):
        return True
    return False

def check_image(image_path):
    image = cv2.imread(image_path)
    if image is None:
        return "Error: Image not found or unable to load."

    aligned_faces = detect_and_align_faces(image)
    if len(aligned_faces) == 0:
        return "No faces detected in the image."

    for face in aligned_faces:
        if is_front_face_and_clear(face):
            return "The image is clear and contains a front-facing human face."

    return "No clear front-facing human face detected in the image."

if __name__ == "__main__":
    image_path = 'a.jpeg'
    result = check_image(image_path)
    print(result)
