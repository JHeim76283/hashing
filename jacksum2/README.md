# Jacksum2

**Jacksum2 is a Jacksum 1.7.0 fork.** <http://www.jonelo.de/java/jacksum/>

Jacksum2 adds a few features not present in Jacksum 1.7.0.

 - Combined hashes are computed in parallel if more than one processor is available.
 - Made bouncycastle and gnucrypto external dependencies.
 - Upgraded bouncycastle provider version to 1.52
 - Upgraded gnucrypto provider to version 2.0.1
 - New hash algorithms: SHA-3, Skein, SM3, RabinHash
 - Improved performance in some algorithms
 - Built with Maven
 - Automated hash tests
 - Automated benchmarks using JMH (in a separate project) <http://openjdk.java.net/projects/code-tools/jmh/>
