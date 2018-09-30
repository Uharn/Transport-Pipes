package de.robotricker.transportpipes.rendersystems.pipe.modelled.model.data;

import de.robotricker.transportpipes.ducts.pipe.Pipe;
import de.robotricker.transportpipes.ducts.types.PipeType;
import de.robotricker.transportpipes.location.TPDirection;

public class ModelledPipeConnectionModelData {

    private PipeType pipeType;
    private TPDirection connectionDir;

    public ModelledPipeConnectionModelData(PipeType pipeType, TPDirection connectionDir) {
        this.pipeType = pipeType;
        this.connectionDir = connectionDir;
    }

    public TPDirection getConnectionDir() {
        return connectionDir;
    }

    public PipeType getPipeType() {
        return pipeType;
    }

    public static ModelledPipeConnectionModelData createConnectionModelData(Pipe pipe, TPDirection connectionDir) {
        if (pipe.getDuctType().is("Iron")) {
            return new ModelledIronPipeConnectionModelData(connectionDir, false);
        } else if (pipe.getDuctType().is("Extraction")) {
            return new ModelledExtractionPipeConnectionModelData(connectionDir, false);
        } else {
            return new ModelledPipeConnectionModelData(pipe.getDuctType(), connectionDir);
        }
    }

}
