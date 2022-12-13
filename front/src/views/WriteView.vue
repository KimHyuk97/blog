<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const title = ref<string>('');
const content = ref<string>('');

const router = useRouter();

const write = () => {
    console.log(title.value);
    console.log(content.value);

    axios.post("http://localhost:8080/api/posts", {
        title: title.value,
        content: content.value
    })
        .then(response => {
            router.replace({ name: 'home' })
        })
}

</script>

<template>
    <div>
        <el-input v-model="title" placeholder="제목을 입력해주세요" />
    </div>

    <div class="mt-2">
        <el-input v-model="content" type="textarea" rows="15" />
    </div>

    <div class="mt-2">
        <div class="d-flex justify-content-end">
            <el-button type="primary" @click="write()">작성완료</el-button>
        </div>
    </div>
</template>