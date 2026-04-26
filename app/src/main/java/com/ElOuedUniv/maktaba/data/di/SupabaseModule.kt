@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {
    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://ugwczdihnwaugmuhoqrm.supabase.co",
            supabaseKey = "sb_publishable_bFe1rm5OnZKwz5-TaSMDSA_obHg08PS"
        ) {
            install(Postgrest)
            install(Storage)
        }
    }
}
