<template>
  <v-data-table :headers="headers" :items="priceBook" sort-by="calories" class="elevation-1">
    <template v-slot:top>
      <v-toolbar flat color="white">
        <v-toolbar-title>Price Book</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="newPrice" label="New Price"></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="save">Save</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-icon small class="mr-2" color="blue" @click="editItem(item)">mdi-pencil</v-icon>
    </template>
    <template v-slot:no-data>
      <v-btn color="primary" @click="initialize">Reset</v-btn>
    </template>
  </v-data-table>
</template>

<script>
import api from "../backend-api";

export default {
  data: () => ({
    dialog: false,
    headers: [
      { text: "ID", value: "id" },
      { text: "Type", value: "type" },
      { text: "Price ($)", value: "price" },
      { text: "Action", value: "actions" },
    ],
    priceBook: [],
    editedIndex: -1,
    newPrice: null,
    itemForEdit: null,
  }),

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "New Item" : "Edit Item";
    },
  },

  watch: {
    dialog(val) {
      val || this.close();
    },
  },

  created() {
    this.initialize();
  },
  mounted() {
    api.setAuthentication().defaults.headers["Authorization"] =
      "Bearer " + localStorage.getItem("token");
    api.getAllPrices().then((response) => {
      this.priceBook = response.data;
    });
  },

  methods: {
    initialize() {
      this.priceBook = [];
    },

    editItem(item) {
      this.itemForEdit = item;
      this.dialog = true;
    },

    deleteItem(item) {
      const index = this.priceBook.indexOf(item);
      confirm("Are you sure you want to delete this item?") &&
        this.priceBook.splice(index, 1);
    },

    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    save() {
      this.itemForEdit.price = this.newPrice;
      api.EditPrice(this.itemForEdit).then((response) => {
        api.getAllPrices().then((response) => {
          this.priceBook = response.data;
        });
      });
      this.close();
    },
  },
};
</script>