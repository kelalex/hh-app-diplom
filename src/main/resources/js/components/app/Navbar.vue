<template>
  <div >
    <v-app-bar :clipped-left="$vuetify.breakpoint.lgAndUp" color="#f48337" dark app flat height="70" >
      <v-toolbar-title>
        <router-link to="/" >
          <v-btn>Home</v-btn>
        </router-link>
      </v-toolbar-title>
      <v-spacer></v-spacer>

      <v-btn v-if="auth" small tile outlined class="ml-5" @click="logout()">
        <span>Выход</span>
        <v-icon>mdi-login-variant</v-icon>
      </v-btn>

      <v-btn v-else small tile outlined class="ml-5" @click="toLogin()">
        <span>Вход</span>
        <v-icon>mdi-login-variant</v-icon>
      </v-btn>

    </v-app-bar>
  </div>
</template>

<script>
import axios from "axios";

const path = require('path');

export default {
  data() {
    return {
      collapseOnScroll: true,
      loading: false,
      logoPath: path.join(__dirname, 'src', 'main', 'resources', 'assets', 'logo1.png')
    }
  },
  methods: {
    toLogin() {
      this.$router.push('/login')
    },
    logout() {
      axios.post('/user/logout').then(() => {
        this.$store.dispatch('logout')
        this.$router.push('/')
      })
    },
  },
  computed: {
    auth () {
      return this.$store.state.currentUser
    },
  }
}
</script>

<style scoped>


.v-input__slot {
  width: 160px !important;
}

.mtb {
  width: 15%;
  margin-left: -40px;
}
.searchedItem:hover {
  background-color: #f2f2f2
}
.display-result {
  position: absolute;
  color: black;
  width: 50%;
  left: 30%;
  top: 100%;
  max-height: 400px;
  overflow: auto;
  box-shadow: 0 5px 13px 0 rgba(0,0,0,.2);
  background-color: white !important;
  border: 1px solid #ececec;
  padding: 15px;
  z-index: 50;
}
</style>
