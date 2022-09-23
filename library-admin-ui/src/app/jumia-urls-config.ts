export class JumiaUrlsConfig {
    public static get ENVIROMENT(): string {
        return "http://localhost:8080/";
    }

    public static get COUNTRIES_URL(): string {
        return this.ENVIROMENT + "countries";
    }

    public static get CUSTOMERS_FILTER_URL(): string {
        return this.ENVIROMENT + "customers/filter?";
    }
}
