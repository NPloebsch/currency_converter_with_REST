FROM node:18-alpine

WORKDIR /app

COPY . .
COPY package.json .
COPY CurrencyDao.java .
COPY CurrencyConverter.java .
COPY Currency.java . 

RUN yarn install --production

CMD ["node", "index.js"]

EXPOSE 3000
