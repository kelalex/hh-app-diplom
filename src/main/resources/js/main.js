import Vue          from 'vue'
import VueResource  from 'vue-resource'
import router       from "./router";
import App          from "pages/App.vue"
import Vuetify      from "vuetify";
import BootstrapVue from 'bootstrap-vue'
import Vuesax       from 'vuesax'
import 'vuetify/dist/vuetify.min.css'
import 'vue-material-design-icons/styles.css';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'vuesax/dist/vuesax.css'
import 'material-icons/iconfont/material-icons.css';
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import store from 'store'
import VueMask from 'v-mask'
import VueGlide from 'vue-glide-js'
import 'vue-glide-js/dist/vue-glide.css'

Vue.use(VueResource);
Vue.use(Vuetify);
Vue.use(BootstrapVue);
Vue.use(Vuesax);
Vue.use(VueMask);
Vue.use(VueGlide)

new Vue({
    el: '#app',
    vuetify: new Vuetify({
        breakpoint: {
            thresholds: {
                xs: 340,
                sm: 540,
                md: 800,
                lg: 1280,
            },
            scrollBarWidth: 24,
        }
    }),
    router,
    store,
    render: a => a(App)
});