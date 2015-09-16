# rf-push-test

To build use `mvn clean package`

To deploy on Wildfly 10 just copy the `./target/rf-push-test.war` to `WFLY_HOME/standalone/deployments` directory.

I want the table of all invoices to be automatically updated after creation of new inovoice via `a4j:ajax`, but it doesn't work for some reason.

At least in did not, but see latest commit for fix.
