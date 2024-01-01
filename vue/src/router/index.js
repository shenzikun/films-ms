import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Manager',
            component: () => import('@/views/Manager.vue'),
            redirect: '/login',
            children: [
                {path: 'home', name: 'Home', component: () => import('@/views/manager/Home.vue')},
                {path: 'department', name: 'Department', component: () => import('@/views/manager/Department.vue')},
                {path: 'employee', name: 'Employee', component: () => import('@/views/manager/Employee.vue')},
                {path: 'person', name: 'Person', component: () => import('@/views/manager/Person.vue')},
                {path: 'inventory', name: 'Inventory', component: () => import('@/views/manager/Inventory.vue')},
                {path: 'news', name: 'News', component: () => import('@/views/manager/News.vue')},

            ]
        },
        {
            path: '/login',
            name: 'Lgoin',
            component: () => import('@/views/Login.vue'),
        }
    ]
})

export default router
