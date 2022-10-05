<template>
  <v-app>
    <AppBar />    

    <v-main>
      <v-container>
        <Nuxt />
      </v-container>
    </v-main>
    <v-footer
      :absolute="!true"
      app
    >
      <span>&copy; {{ new Date().getFullYear() }} by deirdreamuel</span>
    </v-footer>
    </v-app>
</template>

<script lang="ts">
import AppBar from '~/components/navigation/AppBar.vue'

import { NavItem } from '~/models/navbar'

export default {
  name: 'DefaultLayout',
  components: { AppBar },
  setup () {
    const items: NavItem[] = [
        {
            icon: 'mdi-apps',
            title: 'Welcome',
            to: '/'
        },
        {
            icon: 'mdi-chart-bubble',
            title: 'Inspire',
            to: '/inspire'
        }
    ]

    return { items }
  }
}
</script>
