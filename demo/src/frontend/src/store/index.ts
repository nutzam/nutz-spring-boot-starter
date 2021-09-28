import Vue from "vue";
import Vuex from "vuex";
import getters from "./getters";
import { AppState } from "./modules/app";
import { UserState } from "./modules/user";

Vue.use(Vuex);

export interface RootState {
  app: AppState;
  user: UserState;
}
// Declare empty store first, dynamically register all modules later.
export default new Vuex.Store<RootState>({
  getters,
});
