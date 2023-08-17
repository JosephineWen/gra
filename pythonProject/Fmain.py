from flask import Flask, request, jsonify
from torchvision import models, transforms
from PIL import Image
import torch

app = Flask(__name__)

# 加载预训练模型
model = models.resnet50(pretrained=True)
model.eval()

# 定义图像预处理的转换
preprocess = transforms.Compose([
    transforms.Resize(256),
    transforms.CenterCrop(224),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
])

# 加载ImageNet数据集的标签
LABELS_URL = "https://raw.githubusercontent.com/anishathalye/imagenet-simple-labels/master/imagenet-simple-labels.json"
LABELS = torch.hub.load_state_dict_from_url(LABELS_URL)


@app.route('/predict', methods=['POST'])
def predict():
    # 获取上传的图像文件
    file = request.files['image']
    image = Image.open(file)

    # 预处理图像
    image = preprocess(image)

    # 添加一个维度以适应模型的输入
    image = image.unsqueeze(0)

    # 使用模型进行预测
    with torch.no_grad():
        output = model(image)
        _, predicted_idx = torch.max(output, 1)

    # 返回预测结果
    predicted_label = LABELS[predicted_idx.item()]
    return jsonify({'label': predicted_label})


if __name__ == '__main__':
    app.run()
