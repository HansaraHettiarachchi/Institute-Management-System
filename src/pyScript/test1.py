# import cv2

# # Load the pre-trained Caffe model
# model_file = "res10_300x300_ssd_iter_140000.caffemodel"
# config_file = "deploy.prototxt"
# net = cv2.dnn.readNetFromCaffe(config_file, model_file)

# # Set the backend to CUDA (if available)
# net.setPreferableBackend(cv2.dnn.DNN_BACKEND_CUDA)
# net.setPreferableTarget(cv2.dnn.DNN_TARGET_CUDA)

# # Load the image
# image = cv2.imread("images/14.jpg")
# (h, w) = image.shape[:2]

# # Prepare the input blob for the model
# blob = cv2.dnn.blobFromImage(image, 1.0, (300, 300), (104.0, 177.0, 123.0))

# # Pass the blob through the network
# net.setInput(blob)
# detections = net.forward()

# # Loop over the detections
# for i in range(0, detections.shape[2]):
#     confidence = detections[0, 0, i, 2]

#     # Filter out weak detections
#     if confidence > 0.5:
#         # Get the bounding box coordinates
#         box = detections[0, 0, i, 3:7] * np.array([w, h, w, h])
#         (startX, startY, endX, endY) = box.astype("int")

#         # Crop the face
#         cropped_face = image[startY:endY, startX:endX]

#         # Save or display the cropped face
#         cv2.imwrite("C:\\Users\\Hansara\\Documents\\NetBeansProjects\\Quantum-Qube-master\\pyScript\\detected_faces\\cropped_face.jpg", cropped_face)
#         cv2.imshow("Cropped Face", cropped_face)
#         cv2.waitKey(0)
#         cv2.destroyAllWindows()