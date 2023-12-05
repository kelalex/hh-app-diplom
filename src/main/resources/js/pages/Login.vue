<template>
  <v-content>
    <v-progress-linear indeterminate color="#e52d00" v-if="loading"></v-progress-linear>
    <v-container fluid>
      <v-row align="center" justify="center">
        <v-col cols="6">
          <v-card>
            <!--Форма логина-->
            <v-toolbar color="#f48337" flat dense>
              <v-col>
                <v-toolbar-title style="color: #fafafa; ">Авторизация</v-toolbar-title>
              </v-col>
            </v-toolbar>
            <v-card-text>
              <v-form>
                <v-text-field id="usernameForLogin"
                              name="usernameForLogin"
                              color="#e52d00"
                              prepend-icon="mdi-phone"
                              type="text"
                              v-mask="'7-###-###-##-##'"
                              v-model="usernameForLogin"
                              label="Номер телефона +7"
                              :error-messages="usernameLoginErrors"
                              required
                              @input="$v.usernameForLogin.$touch()"
                              @blur="$v.usernameForLogin.$touch()"
                ></v-text-field>
                <v-text-field id="passwordForLogin"
                              name="passwordForLogin"
                              color="#e52d00"
                              prepend-icon="mdi-key"
                              type="password"
                              v-model="passwordForLogin"
                              label="Пароль"
                              :error-messages="passwordLoginErrors"
                              required
                              @input="$v.passwordForLogin.$touch()"
                              @blur="$v.passwordForLogin.$touch()"
                ></v-text-field>
              </v-form>
            </v-card-text>

            <!--    ОШИБКИ И ОТВЕТЫ        -->
            <v-card-actions v-if="hasAuthResponse">
              <v-alert width="100%" outlined :type="authResponse.status" prominent border="left">
                {{authResponse.message}}
              </v-alert>
            </v-card-actions>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="success" :disabled="!usernameForLogin || !passwordForLogin" block @click="loginUser()">Войти</v-btn>
            </v-card-actions>

            <v-divider></v-divider>

            <!--Форма регистрации-->
            <v-card-text>
              <v-row justify="center">
                Зашли впервые?
              </v-row>
            </v-card-text>
            <v-card-actions>
              <v-dialog v-model="dialog" persistent max-width="500">
                <template v-slot:activator="{ on }">
                  <v-btn color="#e52d00" outlined block dark v-on="on" @click="registrationError = false">
                    Зарегистрируйтесь
                  </v-btn>
                </template>
                <v-card>

                  <v-card-title class="headline">
                    <v-icon>
                      mdi-account-plus
                    </v-icon>
                    <span class="ml-3">Регистрация</span>
                  </v-card-title>
                  <v-card-text>Введите ваши контактные данные</v-card-text>
                  <v-card-text>
                    <v-form>
                      <v-text-field id="username"
                                    color="#e52d00"
                                    label="Номер телефона"
                                    name="username"
                                    prepend-icon="mdi-phone"
                                    type="text"
                                    v-mask="'7-###-###-##-##'"
                                    v-model="username"
                                    :error-messages="usernameErrors"
                                    required
                                    @input="$v.username.$touch()"
                                    @blur="$v.username.$touch()"
                      ></v-text-field>

                      <v-row>
                        <v-col>
                          <v-text-field id="password"
                                        name="password"
                                        prepend-icon="mdi-key"
                                        type="password"
                                        v-model="password"
                                        label="Пароль"
                                        :error-messages="passwordErrors"
                                        required
                                        color="#e52d00"
                                        @input="$v.password.$touch()"
                                        @blur="$v.password.$touch()"
                          ></v-text-field>
                        </v-col>

                        <v-col>
                          <v-text-field id="passwordConfirm"
                                        name="passwordConfirm"
                                        type="password"
                                        v-model="registerPassConfirm"
                                        label="Подтвердите пароль"
                                        :error-messages="passwordConfirmErrors"
                                        required
                                        color="#e52d00"
                                        @input="$v.registerPassConfirm.$touch()"
                                        @blur="$v.registerPassConfirm.$touch()"
                          ></v-text-field>
                        </v-col>
                      </v-row>

                      <v-row>
                        <v-col>
                          <v-text-field id="lastName"
                                        name="lastName"
                                        prepend-icon="mdi-account"
                                        type="text"
                                        v-model="lastName"
                                        label="Фамилия"
                                        :error-messages="lastNameErrors"
                                        required
                                        color="#e52d00"
                                        @input="$v.lastName.$touch()"
                                        @blur="$v.lastName.$touch()"
                          ></v-text-field>
                        </v-col>

                        <v-col>
                          <v-text-field id="firstName"
                                        name="firstName"
                                        type="text"
                                        v-model="firstName"
                                        label="Имя"
                                        :error-messages="firstNameErrors"
                                        required
                                        color="#e52d00"
                                        @input="$v.firstName.$touch()"
                                        @blur="$v.firstName.$touch()"
                          ></v-text-field>
                        </v-col>

                        <v-col>
                          <v-text-field id="patronymic"
                                        name="patronymic"
                                        type="text"
                                        v-model="patronymic"
                                        label="Отчество"
                                        :error-messages="patronymicErrors"
                                        required
                                        color="#e52d00"
                                        @input="$v.patronymic.$touch()"
                                        @blur="$v.patronymic.$touch()"
                          ></v-text-field>
                        </v-col>
                      </v-row>

                      <v-text-field id="email"
                                    name="email"
                                    prepend-icon="mdi-email"
                                    type="email"
                                    v-model="email"
                                    color="#e52d00"
                                    label="E-mail"
                      ></v-text-field>
                    </v-form>
                  </v-card-text>
                  <v-card-actions>
                    <div class="flex-grow-1"></div>
                    <v-btn color="green darken-1" text @click="submitRegistration()">Регистрация</v-btn>
                    <v-btn color="red darken-1" text @click="dialog = false">Отмена</v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-content>
</template>

<script>
import axios from 'axios'
import {validationMixin} from 'vuelidate'
import {required, minLength, email} from 'vuelidate/lib/validators'
export default {
  mixins: [validationMixin],
  validations: {
    username: {required, minLength: minLength(15)},
    password: {required},
    usernameForLogin: {required, minLength: minLength(15)},
    passwordForLogin: {required},
    registerPassConfirm: {required},
    lastName: {required, minLength: minLength(2)},
    firstName: {required, minLength: minLength(2)},
    patronymic: {required, minLength: minLength(2)},
  },
  data() {
    return {
      dialog: false,
      loading: true,
      username: '',
      password: '',
      registerPassConfirm: '',
      lastName: '',
      firstName: '',
      patronymic: '',
      email: '',
      usernameForLogin: '',
      passwordForLogin: '',
      authResponse: {
        hasLoginResponse: false,
        status:'',
        message: ''
      }
    }
  },
  created() {
    this.loading = false
  },
  methods: {
    submitRegistration() {
      this.clearLoginResponse()
      this.$v.$touch()
      if (this.registrationValid) {
        let registrationData = {}
        registrationData['email'] = this.email
        registrationData['username'] = this.username
        registrationData['password'] = this.password
        registrationData['lastName'] = this.lastName
        registrationData['firstName'] = this.firstName
        registrationData['patronymic'] = this.patronymic
        axios.post('/auth/user/registration', registrationData).then(() => {
          this.setAuthResponse(true, 'success', 'Пользователь успешно зарегестрирован!')
        })
            .catch((error) => {
              this.setAuthResponse(true, 'error', 'Ошибка при регистрации!')
            })
        this.dialog = false
      }
    },
    loginUser() {
      this.clearLoginResponse()
      this.$v.$touch()
      if (this.loginValid) {
        let auth = new FormData();
        auth.set('username', this.usernameForLogin);
        auth.set('password', this.passwordForLogin);
        const config = {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          }
        }
        const loginURL = '/user/login'
        axios.post(loginURL, auth, config).then(() => {
          this.$store.dispatch('login')
        })
            .catch((error) => {
              console.log('error', error)
              this.authResponse.hasLoginResponse = true
              this.authResponse.status = 'warning'
              this.authResponse.message = 'Неверный логин или пароль!'
            })
      }
    },
    clearLoginResponse() {
      this.authResponse.hasLoginResponse = false
      this.authResponse.status = ''
      this.authResponse.message = ''
    },
    setAuthResponse(bool, status, message) {
      this.authResponse.hasLoginResponse = bool
      this.authResponse.status = status
      this.authResponse.message = message
    }
  },
  computed: {
    hasAuthResponse() {
      return this.authResponse.hasLoginResponse
    },
    usernameLoginErrors() {
      const errors = []
      if (!this.$v.usernameForLogin.$dirty) return errors
      !this.$v.usernameForLogin.minLength && errors.push('Номер телефона в формате +7-9xx-xxx-xx-xx')
      !this.$v.usernameForLogin.required && errors.push('Введите номер телефона')
      return errors
    },
    passwordLoginErrors() {
      const errors = []
      if (!this.$v.passwordForLogin.$dirty) return errors
      !this.$v.passwordForLogin.required && errors.push('Введите пароль')
      return errors
    },
    usernameErrors() {
      const errors = []
      if (!this.$v.username.$dirty) return errors
      !this.$v.username.minLength && errors.push('Номер телефона в формате +7-9xx-xxx-xx-xx')
      !this.$v.username.required && errors.push('Введите номер телефона')
      return errors
    },
    passwordErrors() {
      const errors = []
      if (!this.$v.password.$dirty) return errors
      !this.$v.password.required && errors.push('Введите пароль')
      return errors
    },
    passwordConfirmErrors() {
      const errors = []
      if (!this.$v.registerPassConfirm.$dirty) return errors
      !this.$v.registerPassConfirm.required && errors.push('Подтвердите пароль')
      return errors
    },
    lastNameErrors() {
      const errors = []
      if (!this.$v.lastName.$dirty) return errors
      !this.$v.lastName.minLength && errors.push('Не менее 2 символов')
      !this.$v.lastName.required && errors.push('Введите фамилию')
      return errors
    },
    firstNameErrors() {
      const errors = []
      if (!this.$v.firstName.$dirty) return errors
      !this.$v.firstName.minLength && errors.push('Не менее 2 символов')
      !this.$v.firstName.required && errors.push('Введите имя')
      return errors
    },
    patronymicErrors() {
      const errors = []
      if (!this.$v.patronymic.$dirty) return errors
      !this.$v.patronymic.minLength && errors.push('Не менее 2 символов')
      !this.$v.patronymic.required && errors.push('Введите отчество')
      return errors
    },
    loginValid() {
      return this.usernameLoginErrors.length === 0 && this.passwordLoginErrors.length === 0
    },
    registrationValid() {
      return this.usernameErrors.length === 0 && this.passwordErrors.length === 0 &&
          this.lastNameErrors.length === 0 && this.firstNameErrors.length === 0 && this.patronymicErrors.length === 0
    },
  }
}
</script>