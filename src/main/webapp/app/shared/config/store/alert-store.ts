import { Module } from 'vuex';

export type Message = { text: string; displayed: boolean; timeout: number; title: string; type: 'info' | 'warning' };

export interface AlertState {
  messages: Message[];
}
const state: AlertState = {
  messages: []
};

export const alertStore: Module<any, any> = {
  state,
  getters: {
    messages: alertState => alertState.messages
  },
  mutations: {
    // Creates a new message and adds it to the list of messages. Removed displayed messages from the list.
    newMessage(currentState: any, { msg, title, type, timeout }: { msg: string; title: string; type: string; timeout?: number }) {
      currentState.messages = currentState.messages.filter(
        (message: { displayed: boolean; text: string; title: string; type: string; timeout: number }) => {
          return !message.displayed;
        }
      );
      currentState.messages.push({
        displayed: false,
        text: msg,
        title,
        type,
        timeout: timeout ? timeout : 3000
      });
    }
  }
};
