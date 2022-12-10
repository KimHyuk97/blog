<script setup lang="ts">
import axios from 'axios';
import { ref } from 'vue';

type Post = {
  id: number,
  title: string,
  content: string,
}

const posts = ref<Post[]>([]);

axios.get("http://localhost:8080/api/getList?page=1&size=5")
  .then(response => {
    console.log(response);
    response.data.forEach((r: Post) => {
      posts.value.push(r);
    });
  })
  .catch(error => {
    console.log(error);
  })

</script>

<template>
  <ul>
    <li v-for="post in posts" key="post.id">
      <div>{{post.title}}</div>
      <div>{{post.content}}</div>
    </li>
  </ul>
</template>
