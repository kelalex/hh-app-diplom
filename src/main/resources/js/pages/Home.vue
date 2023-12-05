
<template>
  <v-app>
    <v-main>
      <v-container class="form-container">
        <h1>ПРИЛОЖЕНИЕ ДЛЯ АВТОМАТИЗАЦИИ ПОИСКА ВАКАНСИЙ</h1>
        <v-row>
          <v-col cols="12" sm="3">
            <v-text-field
                label="Профессия"
                v-model="profession"
                name="Профессия"
            ></v-text-field>
          </v-col>
          <v-col cols="12" sm="3">
            <v-select
                v-model="city"
                :items="cities"
                label="Локация"
                outlined
                dense
            ></v-select>
          </v-col>
          <v-col cols="12" sm="3">
            <v-select
                v-model="salary"
                :items="salaries"
                label="Зарплата"
                outlined
                dense
            ></v-select>
          </v-col>
          <v-col cols="12" sm="3">
            <input type="checkbox" id="checkbox"  v-model="isRemote">
            <label for="checkbox">{{ "Удаленно" }}</label>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="12">
            <v-text-field
                v-model="email"
                label="Email"
                placeholder="Электронная почта"
                outlined
                dense
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col cols="12" sm="6" md="4">
            <v-btn
                color="success"
                @click="subscribe"
                block="true"

                :disabled="isSubscribed"
                class="mb-2"
            >
              Подписаться на рассылку
            </v-btn>
            <v-btn
                color="error"
                @click="unsubscribe"
                block="true"
                :disabled="!isSubscribed"
            >
              Отдписаться от рассылки
            </v-btn>
            <v-btn
                color="primary"
                @click="fetchData"
                block="true"
            >
              Просмотреть вакансии
            </v-btn>
          </v-col>
        </v-row>
        <v-container>
          <v-row>
            <v-col>
              <h2>
                Вакансии
              </h2>
            </v-col>
          </v-row>
          <!-- Используем v-if для проверки наличия данных перед рендерингом -->
          <v-row v-if="responseData && responseData.items && responseData.items.length > 0">
            <!-- Перебираем массив вакансий с помощью v-for -->
            <v-col cols="12" v-for="(vacancy, index) in responseData.items" :key="index">
              <v-card class="mb-4" outlined>
                <v-row no-gutters>
                  <v-col cols="9" class="pa-4">
                    <div class="headline">{{ vacancy.name }}</div>
                    <div class="subheading">{{ vacancy.area.name }}</div>
                    <div>Тип: {{ vacancy.type.name }}</div>
                    <div>Опубликовано: {{ formatDate(vacancy.published_at) }}</div>
                    <div v-html="removeHighlightText(vacancy.snippet.requirement)"></div>
                    <div>{{ vacancy.snippet.responsibility }}</div>
                  </v-col>
                  <v-col cols="3" class="pa-4 details-col">
                    <div class="details">
                      <div v-if="vacancy.salary" class="py-2">
                        <span v-if="vacancy.salary.from" class="body-2">Зарплата: от {{ vacancy.salary.from }}</span>
                        <span v-if="vacancy.salary.to">до {{ vacancy.salary.to }}</span>
                        <span>{{ vacancy.salary.currency }}</span>
                      </div>
                      <div>График: {{ vacancy.schedule.name }}</div>
                      <div>Опыт: {{ vacancy.experience.name }}</div>
                      <div>Занятость: {{ vacancy.employment.name }}</div>
                    </div>
                  </v-col>
                </v-row>
                <v-card-actions>
                  <v-btn :href="vacancy.alternate_url" text color="primary" target="_blank">Подробнее</v-btn>
                </v-card-actions>
              </v-card>
            </v-col>
          </v-row>
          <v-row v-else>
            <v-col class="text-center">
              <span>Данные о вакансиях не найдены.</span>
            </v-col>
          </v-row>
        </v-container>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import axios from "axios";
export default {
  name: 'App',
  components: {},
  data() {
    return {
      profession: '',
      city: '',
      cities: ['Москва', 'Санкт-Петербург', 'Екатеринбург', 'Челябинск', 'Россия'],
      salary: '',
      salaries: ['40000', '60000', '80000', '100000', '150000', '200000' ],
      email: '',
      isSubscribed: false,

      // fetches
      isRemote: false,
      vacancyId: '',
      responseData: null,
      apiData: {
        baseUrl: 'https://api.hh.ru/vacancies?',
      },
    };
  },
  methods: {
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(dateString).toLocaleDateString('ru-RU', options);
    },
    removeHighlightText(text) {
      if (!text) return '';
      return text.replace(/<highlighttext>/g, '').replace(/<\/highlighttext>/g, '');
    },
    subscribe() {
      this.isSubscribed = true;
      alert("Subscribed with " + this.email);
    },
    unsubscribe() {
      this.isSubscribed = false;
      alert("Unsubscribed");
    },
    fetchData() {
      let profession = this.profession;
      let city = '';
      let salary = this.salary;
      let isRemote = this.isRemote;

      switch (this.city) {
        case "Москва": city = '1'; break
        case "Санкт-Петербург": city = '2'; break
        case "Екатеринбург": city = '3'; break
        case "Челябинск": city = '104'; break
        case "Россия": city = '113'; break
      }

      const apiUrl = this.apiData.baseUrl + 'text=' + profession + (isRemote ? '&remote=true' : '') + '&area=' + city + '&salary=' + salary;
      console.log(apiUrl);

      // Запрос на HH
      axios.get(apiUrl)
          .then(response => {
            this.responseData = response.data;
          })
          .catch(error => {
            console.error("Error fetching data:", error);
            this.responseData = null;
          });
    },
  }
}
</script>

<style scoped>
.form-container {
  padding-top: 5vh;
  padding-left: 30vh;
  padding-right: 30vh;
  text-align: center;
}

/* Styling the buttons to be in a vertical list */
.v-btn {
  margin-top: 10px;
}

/* Adjusting the styles of the select inputs for a better user experience */
.v-select, .v-text-field {
  margin-bottom: 16px;
}

.checkbox-active .v-label {
  font-weight: bold; /* Сделать текст жирным при активации чекбокса */
}

/* Стили для чекбокса, когда он не активен */
.v-checkbox--is-disabled .v-icon,
.v-checkbox:not(.v-checkbox--is-disabled) .v-icon:not(.v-icon--disabled) {
  color: rgba(0, 0, 0, 0.54); /* Серый цвет для иконки */
}

/* Стили для чекбокса, когда он активен */
.v-checkbox.v-input--is-checked .v-icon {
  color: #7B1FA2; /* Цвет при активации (в вашем случае deep-purple accent-2) */
}

.v-checkbox .v-input--selection-controls__ripple {
  opacity: 0.12; /* Определяет непрозрачность ripple-эффекта */
}

/* Увеличить размер иконки чекбокса */
.v-checkbox .v-icon {
  font-size: 24px; /* Размер иконки */
}

.highlight {
  background-color: yellow;
}

.highlight {
  background-color: #FFEB3B; /* Подсветка для текста */
}

.v-card {
  box-shadow: 5px 5px 15px rgba(0,0,0,0.2);
  transition: box-shadow 0.3s ease-in-out;
}

.v-card:hover {
  box-shadow: 10px 10px 20px rgba(0,0,0,0.3);
}

.v-card-horizontal .v-card__text {
  border-left: 1px solid rgba(0,0,0,.1);
}

.headline {
  font-size: 24px;
  font-weight: bold;
}

.subheading {
  font-size: 16px;
  color: #5C6BC0;
  font-weight: bold;
}

.body-2 {
  font-size: 14px;
  color: rgba(0,0,0,.6);
}

.highlight {
  background-color: #FFEB3B;
  padding: 0 4px;
}

.headline {
  font-size: 20px; /* Увеличиваем размер шрифта для заголовка */
}
.subheading {
  color: #5C6BC0; /* Добавляем цвет подзаголовку */
}
.body-2 {
  font-weight: bold; /* Делаем текст жирным */
}
.details {
  font-size: 14px; /* Размер шрифта для деталей вакансии */
  text-align: right; /* Выравниваем текст деталей вакансии по правому краю */
}
.details-col {
  border-left: 1px solid rgba(0,0,0,.1); /* Добавляем линию для визуального разделения */
}

.text-end {
  text-align: right; /* Выравнивание текста по правому краю */
}

.text-start {
  text-align: left; /* Выравнивание текста по левому краю */
}

.details-col {

}
</style>
