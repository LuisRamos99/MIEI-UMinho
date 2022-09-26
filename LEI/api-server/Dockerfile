FROM strapi/strapi

WORKDIR /app

ENV NODE_ENV production

COPY ./package.json ./

RUN npm install

COPY . .


EXPOSE 1337

CMD [ "npm" , "run" , "develop" ]