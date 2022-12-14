<template>
  <div id="he-plugin-standard"></div>
</template>

<script>
  import '../utils/weather';
    export default {
      name: "weather",
      methods: {
        getWeather() {
          const s = document.createElement('script');
          s.type = 'text/javascript';
          s.src = "https://widget.qweather.net/standard/static/js/he-standard-common.js?v=2.0";
          document.body.appendChild(s);
        },
      },
      mounted() {
        this.getWeather();
      }
    }
</script>

<style scoped>

</style>
