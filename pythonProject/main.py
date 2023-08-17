from flask import Flask, request, jsonify
from PIL import Image
import tensorflow as tf
from tensorflow.keras.applications.resnet50 import ResNet50, preprocess_input, decode_predictions
import os

app = Flask(__name__)

# 获取当前目录
current_dir = os.path.dirname(os.path.abspath(__file__))

# 模型文件相对于当前目录的路径
model_path = os.path.join(current_dir, 'resnet_model.h5')

# 加载预训练的ResNet50模型
model = tf.keras.models.load_model(model_path)

@app.route('/predict', methods=['POST'])
def predict():
    # 获取上传的图像文件
    file = request.files['image']
    image = Image.open(file)

    # 调整图像大小为ResNet50所需的尺寸
    image = image.resize((224, 224))

    # 将图像转换为Numpy数组并进行预处理
    image_array = tf.keras.preprocessing.image.img_to_array(image)
    image_array = preprocess_input(image_array)
    image_array = tf.expand_dims(image_array, 0)

    # 利用模型进行预测
    predictions = model.predict(image_array)

    # 解码预测结果
    decoded_predictions = decode_predictions(predictions, top=3)[0]

    # 获取预测结果标签和置信度
    labels = [label for (_, label, confidence) in decoded_predictions]
    confidences = [confidence for (_, _, confidence) in decoded_predictions]
    print(labels)
    print(confidences)

    # 构建响应的 JSON 格式数据
    response_data = {
        'labels': labels,
        'confidences': confidences
    }

    # 返回 JSON 格式的响应
    return jsonify(labels)

if __name__ == '__main__':
    app.run()
