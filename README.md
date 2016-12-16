# Unbxd Java SDK

## Documentation
Java Language bindings for Unbxd Restful APIs [Docs](http://unbxd.github.io/java-sdk/docs)

## Usage
### Install
```bash
$ mvn clean install
```

### Deployment
Deployment requires GPG or PGP key to sign artifacts.
If you don't have one refer  [Generate PGP Signatures](http://blog.sonatype.com/2010/01/how-to-generate-pgp-signatures-with-maven/).

Push build to staging repository
``` bash
$ mvn deploy -DperformRelease=true -Dgpg.passphrase=**** -Dgpg.keyname=<your key ID>
```

To Promote the published artifacts from the staging to the release repository refer [Releasing project to maven central repository](http://jroller.com/holy/entry/releasing_a_project_to_maven).

