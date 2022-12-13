<script lang="ts" setup>
import { RouterView } from "vue-router";
import Header from "@/components/Header.vue"
import Content from "@/components/Content.vue"
</script>

<template>
  <Header/>
  <Content/>
</template>

<style>
  body {
    margin: 0;
    font-family: -apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo", "Pretendard Variable", Pretendard, Roboto, "Noto Sans KR", "Segoe UI", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
  }
</style>