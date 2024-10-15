import { useRoute, useRouter } from 'vue-router'
import { AppRoutePaths } from '../AppRoutes'

export class UrlService {
  private route = useRoute()
  private router = useRouter()

  getBasePath(): string {
    return this.route.fullPath
  }

  redirect(path: AppRoutePaths): void {
    this.router.push(path)
  }
}
