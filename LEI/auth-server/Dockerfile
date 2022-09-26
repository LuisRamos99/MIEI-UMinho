FROM node
WORKDIR /auth-server
COPY package.json /auth-server
RUN npm install
COPY . /auth-server
CMD ["npm", "start"]
