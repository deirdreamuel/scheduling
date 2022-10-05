<template>
  <div class="text-center">
    <v-menu
      v-model="menu"
      :close-on-content-click="false"
      offset-y
      content-class="elevation-0"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn text class="appMenu" v-on="on" v-bind="attrs">
          <div class="d-flex justify-space-around">
            <div>
              <v-sheet
                class="rounded-circle"
                width="30px"
                height="30px"
                :color="avatar"
              />
            </div>
            <div class="d-flex align-self-center pl-1">
              <v-icon dense
                >mdi-chevron-{{ menu ? "up" : "down" }}</v-icon
              >
            </div>
          </div>
        </v-btn>
      </template>

      <v-card outlined>
          <div class="d-flex justify-space-between">
            <v-btn text @click="menu = false">
              <v-icon x-small>
                  mdi-window-minimize
              </v-icon>
            </v-btn>
            <v-btn text @click="menu = false">
              <v-icon small @click="router.push('/')">
                  mdi-dock-window
              </v-icon>
            </v-btn>
          </div>
        <v-divider></v-divider>
        
        <v-list>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>{{ fullname }}</v-list-item-title>
              <v-list-item-subtitle>{{ username }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>

        <v-divider></v-divider>

        <div>
            <v-btn text block  @click="loginPage">
                LOG IN
            </v-btn>
        </div>
      </v-card>
    </v-menu>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, useRouter } from "@nuxtjs/composition-api"
export default defineComponent({
  name: "ProfileMenu",
  setup() {
    const menu = ref(false);
    const randomColor = () => {
      const r = () => Math.floor(256 * Math.random())
      const b = () => Math.floor(256 * Math.random())
      const g = () => Math.floor(256 * Math.random())

      return `rgb(${r()}, ${g()}, ${b()})`
    };

    const router = useRouter()
    const loginPage = () =>  {
        menu.value = !menu.value
        router.push('/login')
    }

    const avatar = ref(randomColor())
    const fullname = ref('Default Player')
    const username = ref(`@default#342421`)

    return {
      menu,
      avatar,
      fullname,
      username,
      loginPage,
      router
    };
  },
});
</script>

<style>
.nuxt-link {
  background: red;
}
</style>