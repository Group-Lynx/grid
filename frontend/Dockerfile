FROM oven/bun:1

WORKDIR /app

COPY . .

# Install dependencies & build
RUN bun install --frozen-lockfile --production
RUN bun run build

ENV API_SERVER_HOST=grid-backend

EXPOSE 3000

CMD [ "bun", ".output/server/index.mjs" ]
