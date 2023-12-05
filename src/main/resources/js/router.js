import Vue          from 'vue';
import VueRouter    from "vue-router";
import Login        from "pages/Login.vue";
import Home         from "pages/Home.vue";

Vue.use(VueRouter);
const routes = [
    {path: '/', component: Home},
    {path: '/login', component: Login},
];

export default new VueRouter({
    mode: 'history',
    routes
})