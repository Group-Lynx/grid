// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  modules: ["nuxt-primevue", "@nuxtjs/tailwindcss"],
  primevue: {
    usePrimeVue: true,
    options: {
      unstyled: false,
      ripple: true,
    },
    cssLayerOrder: "tailwind-base, primevue, tailwind-utilities",
  },
  css: [
    "assets/css/tailwind.css",
    "primevue/resources/themes/lara-light-blue/theme.css",
    "primeicons/primeicons.css",
  ],
  postcss: {
    plugins: {
      tailwindcss: {},
      autoprefixer: {},
    },
  },
  imports: {
    dirs: ["types"],
  },
  runtimeConfig: {
    apiServerBase: "http://127.0.0.1:8000",
  },
});
