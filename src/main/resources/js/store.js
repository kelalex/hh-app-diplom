import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import createPersistedState from 'vuex-persistedstate'
import router from './router'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        currentUser: null,
    },
    mutations: {
        setCurrentUser(currentState, user) {
            currentState.currentUser = user
        },
        logoutUser(currentState) {
            currentState.currentUser = null
        },
    },
    actions: {
        login(context) {
            return axios.post('/auth/loginUser').then((response) => {
                const user = response.data[0]
                context.commit('setCurrentUser', user)
                router.push('/')
            }).catch(reason => {
                console.log(reason)
            })
        },
        logout(context) {
            context.commit('logoutUser')
            context.commit('setAdminModeFalse')
        },
    },
    plugins: [createPersistedState()]
});