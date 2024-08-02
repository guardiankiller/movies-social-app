import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080/',
        changeOrigin: true,
        // rewrite: (path) => path.replace(/^\/api/, ''),
        configure: (proxy, _options) => {
          proxy.on("error", (err, _req, _res) => {
            console.log("proxy error", err);
          });
          proxy.on("proxyReq", (proxyReq, req, _res) => {
            console.log(
              `Sending Request: ${req.method} ${req.url}`,
              ` => TO THE TARGET => ${proxyReq.host}${proxyReq.path}`,
              JSON.stringify(proxyReq.getHeaders()["content-type"]),
            );
          });
          proxy.on("proxyRes", (proxyRes, req, _res) => {
            console.log(
              "Received Response from the Target:",
              proxyRes.statusCode,
              req.url,
              JSON.stringify(proxyRes.headers["content-type"]),
            );
          });
        },
      },
      '/actuator': {
        target: 'http://localhost:8080/',
        changeOrigin: true,
        // rewrite: (path) => path.replace(/^\/api/, ''),
      }
    }
  }
})
