# LifeGame-Map-Generator
> Use to generate one-way path map by genetic algorithm.


### Output Example
**25** steps, start at **(0, 0)**, end at **E**.
```
[
[↓, →, →, ↓, E, ←, *],
[→, ↑, *, →, ↓, ↑, ←],
[*, *, ↓, ←, ←, →, ↑],
[*, *, →, ↓, *, ↑, ←],
[*, *, *, →, →, →, ↑]
]
```

### [LifeGameGenerator](src/main/java/lifegamemap/LifeGameGenerator.java)
```java
        // prepare possible directions
        List<IDirection> directionGuides = Direction.DIRECTIONS_4;
        
        // prepare genetic algorithm engine
        Engine<IntegerGene, Long> engine = Engine
                .builder(MapWalker.FITNESS, new MapWalkerCodec(directionGuides, 5, 7))
                .build();

        // prepare genetic algorithm statistic
        EvolutionStatistics<Long, ?> statistics = EvolutionStatistics.ofNumber();

        // start engine
        Phenotype<IntegerGene, Long> result = engine
                .stream()
                .limit(500)
                .peek(statistics)
                .collect(toBestPhenotype());

        logger.info("\n" + statistics.toString());
        logger.info("Best fitness: {}", result.getFitness());
        
        // output result
        Cartographer cartographer = Cartographer.builder()
                .directions(directionGuides)
                .data(result)
                .build();
        
        cartographer.output();
    }
```

### Genetic Algorithm Engine
see [jenetics.io](http://jenetics.io/)
