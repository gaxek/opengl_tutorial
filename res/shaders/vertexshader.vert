#version 450 core

in vec3 position;
in vec2 texCoords;

out vec2 pass_texCoords;

uniform mat4 projection;

void main(){
    gl_Position = projection * vec4(position, 1.0);
    pass_texCoords = texCoords;
}
