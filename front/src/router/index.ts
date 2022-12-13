import { createRouter, createWebHistory } from "vue-router";
import ReadView from "@/views/ReadView.vue";
import WriteView from "@/views/WriteView.vue";
import HomeView from "@/views/HomeView.vue";
import EditView from "@/views/EditView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/write",
      name: "write",
      component: WriteView,
    },
    {
      path: "/read/:id",
      name: "read",
      component: ReadView,
      props: true
    },
    {
      path: "/edit/:id",
      name: "edit",
      component: EditView,
      props: true
    },
  ],
});

export default router;
