/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        // Binance-inspired theme colors
        brand: {
          yellow: '#fcd535',
          dark: '#1e2026',
        },
        trade: {
          green: '#0ecb81',
          greenHover: '#0b9e65',
          red: '#f6465d',
          redHover: '#c93346',
        },
        bg: {
          base: '#0b0e11', // Main app background
          panel: '#181a20', // Card/Module background
          hover: '#2b3139',
        },
        text: {
          primary: '#eaecef',
          secondary: '#848e9c',
          muted: '#5e6673',
        },
        border: {
          DEFAULT: '#2b3139',
        }
      },
      fontFamily: {
        sans: ['BinancePlex', 'Inter', 'system-ui', 'sans-serif'],
      },
    },
  },
  plugins: [],
}
