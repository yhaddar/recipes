# Step 1: Build Angular app using Node.js
FROM node:20-alpine as builder

WORKDIR /app

# Copy package.json and install dependencies
COPY package*.json ./
RUN npm install

# Copy the rest of the Angular project and build
COPY . .
RUN npm run build --prod

# Step 2: Serve with Nginx
FROM nginx:alpine

# Copy the built Angular app to the Nginx HTML directory
COPY --from=builder /app/dist/<angular-app-name> /usr/share/nginx/html

# Copy custom Nginx config (optional)
# COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
