import os

# 获取当前目录下的所有 .png 文件
files = [f for f in os.listdir() if f.endswith('.png')]

files.sort()

for i, file in enumerate(files, start=1):
    file_name = os.path.splitext(file)[0]
    # print(file_name)
    new_name = f"{i}.png"
    print(file_name + "-->" + new_name)
    os.rename(file, new_name)

