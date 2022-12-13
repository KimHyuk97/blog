<script setup lang="ts">
import axios from 'axios';
import { onMounted, ref } from 'vue';

type Post = {
  id: number,
  title: string,
  content: string,
}

const posts = ref<Post[]>([]);

onMounted(() => {
  axios.get("http://localhost:8080/api/getList?page=1&size=5")
    .then(response => {
      response.data.forEach((r: Post) => {
        posts.value.push(r);
      })
    })
})

</script>

<template>
  <ul v-if="posts.length > 0">
    <li v-for="post in posts" key="post.id">
      <div>
        <router-link :to="{ name: 'read', params: { id: post.id } }" class="title">
          {{ post.title }}
        </router-link>
      </div>
      <div class="content">{{ post.content }}</div>
    </li>
  </ul>
</template>


<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;
  border: 1px solid #dbdbdb;
  border-left: 0;
  border-right: 0;

  li {
    padding: 1.5rem 0;
    border-bottom: 1px solid #dbdbdb;

    .title {
      font-size: 1.1rem;
      color: #383838;
      text-decoration: none;
      
      &:hover {
        text-decoration: underline;
      }
    }

    .content {
      font-size: 0.85rem;
      margin-top: 8px;
      line-height: 1.4;
      color: #7e7e7e;
    }

    &:last-child {
      border-bottom: 0;
    }

    .sub {
      margin-top: 8px;
      font-size: 0.78rem;

      .regDate {
        margin-left: 10px;
        color: #6b6b6b;
      }
    }
  }
}
</style>