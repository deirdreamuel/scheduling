<template>
  <div class="d-flex py-8">
    <v-row class="d-flex justify-space-between">
      <v-col class="d-flex flex-column">
        <div class="d-flex justify-center">
          <v-sheet v-if="state.selected == -1" outlined rounded color="black">
            <v-card
              elevation="0"
              min-width="350"
              max-width="400"
              class="d-flex justify-center"
              color="red"
              outlined
            >
              <div class="text-overline">calendar</div>
            </v-card>
          </v-sheet>
          <v-sheet v-else outlined rounded color="black">
            <v-card
              :color="state.selected.color"
              elevation="0"
              min-width="350"
              max-width="400"
              class="d-flex justify-center rouded-0"
            >
              <div class="text-overline">calendar</div>
            </v-card>
          </v-sheet>
        </div>

        <div class="d-flex justify-center">
          <v-card
            v-if="state.selected == -1"
            class="black-border pa-5"
            min-width="350"
            max-width="400"
            outlined
          >
            <div class="d-flex justify-center">
              <div>
                <v-card elevation="0" color="#ff4f00">
                  <v-date-picker
                    v-model="picker"
                    no-title
                    color="red"
                    :min="today"
                  ></v-date-picker>
                </v-card>
              </div>
            </div>
          </v-card>
          <v-card
            v-else
            class="black-border pa-5"
            min-width="350"
            max-width="400"
            outlined
          >
            <div class="d-flex justify-center">
              <div>
                <v-card elevation="0">
                  <v-date-picker
                    v-model="picker"
                    no-title
                    :color="state.selected.color"
                    :min="today"
                  ></v-date-picker>
                </v-card>
              </div>
            </div>
          </v-card>
        </div>

        <div class="d-flex justify-center">
          <v-card min-width="350" max-width="400" elevation="0" class="pt-2">
            <div class="d-flex justify-space-between">
              <div class="text-overline">meeting with:<br /></div>
              <div class="text-overline font-weight-bold">deirdre chong</div>
            </div>
            <div class="d-flex justify-space-between">
              <div class="text-overline">interval:<br /></div>
              <div class="text-overline font-weight-bold">30 mins</div>
            </div>
            <div class="d-flex justify-space-between">
              <div class="text-overline">selected date:<br /></div>
              <div class="text-overline font-weight-bold">
                {{ pickerFormatted }}
              </div>
            </div>
          </v-card>
        </div>
      </v-col>

      <!-- available dates -->
      <v-col>
        <!-- <v-card outlined min-width="300">
          <v-skeleton-loader
            v-bind="attrs"
            type="table-heading, list-item-two-line"
          ></v-skeleton-loader>
        </v-card> -->
        <v-row class="d-flex justify-center">
          <div v-if="loading">
            <v-card
              class="black-border"
              min-width="350"
              max-width="375"
              min-height="280"
              max-height="350"
              outlined
            >
              <v-card-title class="black--text white darken-4 overline">
                SELECT INTERVAL FOR {{ pickerFormatted }}
              </v-card-title>
              <v-divider color="black"></v-divider>
              <div class="pa-2">
                <v-skeleton-loader
                  type="table-heading, list-item-two-line"
                ></v-skeleton-loader>
              </div>
            </v-card>
          </div>

          <v-card
            v-else
            class="black-border"
            min-width="350"
            max-width="375"
            outlined
          >
            <v-card-title class="black--text white darken-4 overline">
              SELECT INTERVAL FOR {{ pickerFormatted }}
            </v-card-title>
            <v-divider color="black"></v-divider>

            <v-virtual-scroll
              :items="state.suggestions"
              :item-height="75"
              min-height="280"
              max-height="350"
            >
              <!-- loading results... -->

              <template v-slot:default="{ item }">
                <div class="pa-2">
                  <v-card
                    outlined
                    :color="item.color"
                    class="black-border d-flex justify-space-around"
                  >
                    <v-card-title>
                      <div
                        v-if="state.selected == item"
                        class="text-overline font-weight-bold"
                      >
                        {{ item.start }} - {{ item.end }}
                      </div>
                      <div v-else class="text-overline">
                        {{ item.start }} - {{ item.end }}
                      </div>
                    </v-card-title>
                    <v-list-item-action>
                      <v-btn
                        v-if="state.selected == item"
                        depressed
                        small
                        color="red"
                        @click="state.selected = -1"
                      >
                        SELECTED
                      </v-btn>
                      <v-btn
                        v-else
                        depressed
                        small
                        black
                        dark
                        @click="state.selected = item"
                      >
                        SELECT
                      </v-btn>
                    </v-list-item-action>
                  </v-card>
                </div>
              </template>
            </v-virtual-scroll>
          </v-card>
        </v-row>
        <v-row v-if="state.selected != -1" class="d-flex justify-center">
          <v-card min-width="350" max-width="350" elevation="0" class="pt-6">
            <div class="text-overline font-weight-bold">selected interval</div>
            <div>
              <v-sheet outlined color="black" rounded>
                <v-card
                  outlined
                  :color="state.selected.color"
                  class="black-border d-flex justify-space-around"
                >
                  <v-card-title>
                    <div class="text-overline font-weight-bold">
                      {{ state.selected.start }} - {{ state.selected.end }}
                    </div>
                  </v-card-title>
                  <v-list-item-action>
                    <v-btn depressed small color="red"
                      ><b>{{ state.selected.date }}</b></v-btn
                    >
                  </v-list-item-action>
                </v-card>
              </v-sheet>
            </div>
          </v-card>
        </v-row>
        <v-row class="d-flex justify-center">
          <v-card min-width="350" max-width="350" elevation="0">
            <div class="pt-6">
              <v-row>
                <v-col>
                  <Textfield
                    :label="firstname.label"
                    :value="firstname.val"
                    @update="firstname.update"
                    :dense="true"
                  ></Textfield>
                </v-col>
                <v-col>
                  <Textfield
                    :label="lastname.label"
                    :value="lastname.val"
                    @update="lastname.update"
                    :dense="true"
                  ></Textfield>
                </v-col>
              </v-row>

              <Textfield
                :label="email.label"
                :value="email.val"
                @update="email.update"
                :dense="true"
              ></Textfield>
            </div>
            <div>
              <v-sheet
                v-if="state.selected == -1"
                outlined
                color="black"
                rounded
              >
                <v-btn block color="red"> SCHEDULE </v-btn>
              </v-sheet>

              <v-sheet v-else outlined color="black" rounded>
                <v-btn
                  block
                  :color="state.selected.color"
                  @click="postSchedule"
                >
                  SCHEDULE NOW
                </v-btn>
              </v-sheet>

              <v-sheet v-if="message != ''" outlined rounded>
                <div>
                  <p>{{ message }}</p>
                </div>
              </v-sheet>
            </div>
          </v-card>
        </v-row>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import {
  defineComponent,
  ref,
  useContext,
  onMounted,
  reactive,
  watch,
} from "@nuxtjs/composition-api";

import moment from "moment";
import Textfield from "~/components/fields/textfield.vue";
import { textfield } from "~/models/textfield";

export default defineComponent({
  components: { Textfield },
  setup() {
    const current = new Date(
      Date.now() - new Date().getTimezoneOffset() * 60000
    );

    const picker = ref(current.toISOString().substr(0, 10));
    const today = current.toISOString().substr(0, 10);

    const { $axios } = useContext();

    const data = {
      colors: [
        "#90CAF9",
        "#64B5F6",
        "#42A5F5",
        "#82B1FF",
        "#448AFF",
        "#2979FF",
        "#2962FF",
        "#2196F3",
        "#1E88E5",
        "#1976D2",
        "#1565C0",
        "#0D47A1",
      ],
    };

    const state = reactive({
      suggestions: [],
      selected: -1,
    });

    const loading = ref(false);
    let firstname = new textfield("first name", "");
    let lastname = new textfield("last name", "");
    let email = new textfield("email", "");

    const message = ref("");
    const postSchedule = async () => {
      loading.value = true;
      if (state.selected == -1) {
        return;
      }

      const params = {
        id: "ff808081812d395601812d39c4cf0000",
      };

      const meeting = {
        start_time: state.selected.startISO,
        end_time: state.selected.endISO,
        title: "Meeting",
        notes: "This meeting has been rescheduled",
        participants: [
          {
            first_name: firstname.val,
            last_name: lastname.val,
            email: email.val,
          },
        ],
      };

      await $axios
        .$post("/meetings", meeting, { params: params })
        .then((res) => {
          message.value = res.message;
        });
      loading.value = false;
    };

    const getSuggestions = async () => {
      loading.value = true;
      const params = {
        date: moment(picker.value).format("yyyy-MM-DD"),
      };
      await $axios
        .$get("/suggestions/ff808081812d395601812d39c4cf0000", {
          params: params,
        })
        .then((res) => {
          state.suggestions = [];
          for (let i = 0; i < res.length; i++) {
            let colorPicker = "#90CAF9";
            if (res.length > data.colors.length) {
              colorPicker =
                data.colors[Math.floor(i * (data.colors.length / res.length))];
            } else {
              colorPicker = data.colors[i];
            }

            const start = moment(res[i].start).local().format("hh:mm a") || "";
            const end = moment(res[i].end).local().format("hh:mm a") || "";

            const obj = {
              date: moment(params.date).format("MMMM DD YYYY"),
              color: colorPicker,
              start: start || "",
              startISO: res[i].start || "",
              endISO: res[i].end || "",
              end: end || "",
            };

            state.suggestions.push(obj);
          }
        });
      loading.value = false;
    };

    const pickerFormatted = ref(moment(picker.value).format("MMMM DD YYYY"));
    watch(picker, (val, old) => {
      console.log(val);
      console.log(old);
      getSuggestions();
      pickerFormatted.value = moment(picker.value).format("MMMM DD YYYY");
    });

    return {
      picker,
      pickerFormatted,
      today,
      firstname,
      lastname,
      email,
      state,
      message,
      getSuggestions,
      loading,
      postSchedule,
    };
  },
  async created() {
    await this.getSuggestions();
  },
});
</script>

<style scoped>
.black-border {
  border-color: black;
}

.red-border {
  border-color: black;
}
</style>