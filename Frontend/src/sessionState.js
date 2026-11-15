/** Clear account-owned data before another identity can enter the application. */
export function clearAccountState(state) {
  Object.assign(state, {
    account: null, profile: null, activeRole: null, mustChangePassword: false,
    adminTemporaryPassword: '', phoneVerificationSent: false,
    merchantStoreId: '', editingCatalogItem: '', selectedStore: null,
    storeItems: [], storeProfessionals: [], storeHours: null,
    merchantHours: null, merchantProfessionals: [],
    cart: [], cartOpen: false, cartMode: 'PICKUP', deliveryAddress: '',
    addresses: [], selectedAddressId: '', addressFormOpen: false,
    checkout: null, checkoutNote: '', paymentStarting: false, paymentConnection: null,
    orders: [], reservations: [], deliveries: [], offers: [], ownStores: [],
    notifications: [], platformRequests: [], supportTickets: [], activeTicket: null,
    supportAdminView: false, adminTickets: [], adminServiceCities: [], adminIntegrations: [],
    adminStoreCategories: [], adminAuditEvents: [], adminPlatformRequests: [],
    adminStores: [], adminPages: {}, adminPageMeta: {}, adminSummary: {},
    adminRefunds: [], adminOperators: [], adminAccounts: [], adminApplications: [],
    openChat: null, chatThreads: {}, chatDrafts: {},
    adminLoadError: '', merchantLoadError: '', trackingMapOrderId: '',
    authMode: 'login', recoveryMode: false, recoveryStep: 'request', recoveryEmail: '',
    page: 'home', mobileMenu: false,
  });
}
