import tensorflow as tf
from keras._tf_keras.keras import applications

# # Download the pre-trained EfficientNetV2L model
# model = applications.EfficientNetV2L(weights='imagenet')

# # Print the model summary to verify the successful download
# model.summary()

# # Save the model to a file
# 

# print("Model saved as efficientnetv2l_model.h5")

import json

# Download the pre-trained EfficientNetV2L model
model = applications.ConvNeXtXLarge (weights='imagenet')

# Print the model summary to verify the successful download
model.summary()
model.save('efficientnetv2l_model.h5')
# Convert model layers to JSON format
model_json = model.to_json()

# Save the JSON to a file
with open('efficientnetv2l _layers.json', 'w') as json_file:
    json_file.write(model_json)

print("Model layers saved as efficientnetv2l _layers.json")
