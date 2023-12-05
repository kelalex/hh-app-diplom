<template>
    <v-app class="bg">
        <navbar></navbar>
      <router-view></router-view>
    </v-app>
</template>

<script>
import axios from 'axios'
    import Navbar from "components/app/Navbar.vue";
    export default {
        components: {Navbar},
        beforeCreate() {
            if (this.$store.state.currentUser !== null) {
                axios.post('/auth/checkCorrectLogin').then(response => {
                    if (response.data === true) {
                        this.$store.dispatch('logout')
                    }
                })
            }
        },
    }
</script>

<style scoped>
    .bg{
        background-color: #e1e1e1 !important;
    }
</style>

