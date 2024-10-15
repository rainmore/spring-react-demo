import { library } from '@fortawesome/fontawesome-svg-core'
import { fab } from '@fortawesome/free-brands-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { createApp } from 'vue'
import ToastPlugin from 'vue-toast-notification'
import App from './App.vue'
import 'vue-toast-notification/dist/theme-default.css'
import './main.scss'
import { appRouters } from './AppRoutes'

library.add(fab, fas, far)

createApp(App)
  .use(ToastPlugin, {
    // override the global option
    position: 'bottom-right',
  })
  .use(appRouters)
  .mount('#app')
