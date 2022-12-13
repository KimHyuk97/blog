<script setup lang="ts">
import { onMounted, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();

type Post = {
  id: Number,
  title: string,
  content: string
}

const props = defineProps({
  id: {
    type: Number,
    require: true,
  }
})

const post = ref<Post>({
  id: Number(props.id),
  title: '',
  content: '',
})

const edit = () => {
  axios.patch(`http://localhost:8080/api/posts/${post.value.id}`, post.value)
    .then(response => {
      alert('수정됨')
      router.replace({ name: 'home' })
    })
}

const getPost = async () => {
  axios.get(`http://localhost:8080/api/posts/${props.id}`)
    .then(response => {
      post.value = response.data;
    })
}

onMounted(() => {
  getPost()
})

</script>

<template>
  <div>
    <el-input v-model="post.title" />
  </div>

  <div class="mt-2">
    <el-input v-model="post.content" type="textarea" rows="15" />
  </div>

  <div class="mt-2 d-flex justify-content-end">
    <el-button type="warning" @click="edit()">수정완료</el-button>
  </div>
</template>