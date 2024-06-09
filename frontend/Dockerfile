FROM node:20-alpine

WORKDIR /app

COPY . .

# Fix npm connect error
RUN npm config set registry https://registry.npmmirror.com/

# Install dependencies & build
RUN npm install
RUN npm run build

ENV API_SERVER_HOST=grid-backend

EXPOSE 3000

CMD [ "node", ".output/server/index.mjs" ]
