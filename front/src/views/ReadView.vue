<script setup lang="ts">
import axios from 'axios';
import { onMounted, ref } from 'vue';
import { useRouter } from "vue-router";

type Post = {
  id: number,
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
  id: 0,
  title: "",
  content: "",
})

const getPost = async () => {
  axios.get(`http://localhost:8080/api/posts/${props.id}`)
    .then(response => {
      post.value = response.data;
    })
    .catch(error => {
      console.log(error);
    })
}

const router = useRouter();

const moveToEdit = () => {
  router.push({ name: "edit", params: { postId: props.id } });
};

onMounted(() => {
  getPost()
})

</script>

<template>
  <el-row class="mt-3">
    <el-col>
      <div class="content">{{ post.content }}</div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit()">수정</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.title {
  font-size: 1.6rem;
  font-weight: 600;
  color: #383838;
  margin: 0;
}

.sub {
  margin-top: 10px;
  font-size: 0.78rem;

  .regDate {
    margin-left: 10px;
    color: #6b6b6b;
  }
}

.content {
  font-size: 0.95rem;
  margin-top: 12px;
  color: #616161;
  white-space: break-spaces;
  line-height: 1.5;
}
</style>