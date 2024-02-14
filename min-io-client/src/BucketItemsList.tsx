import React, { useState, useEffect } from 'react';
import {
    List,
    ListItem,
    ListItemText,
    Collapse,
    Typography,
    Button,
    Dialog,
    DialogTitle,
    DialogContent, DialogActions, DialogContentText
} from '@mui/material';
import { ExpandLess, ExpandMore } from '@mui/icons-material';

interface BucketItemListProps {
    items: ListItemData[];
}

interface Owner {
    id: string;
    displayName: string;
}
export interface ListItemData {
    objectName: string;
    etag: string;
    lastModified: string;
    owner: Owner;
    size: number
}


const BucketItemsList: React.FC<BucketItemListProps> = ({items}) => {
    const [openIndex, setOpenIndex] = useState<number | null>(null);
    const [isDownloadModalOpen, setIsDownloadModalOpen] = useState<boolean>(false);

    const handleClick = (index: number) => {
        setOpenIndex(openIndex === index ? null : index);
    };

    const humanReadableSize = (size: number) => {
        const i = size === 0 ? 0 : Math.floor(Math.log(size) / Math.log(1024));
        return (size / Math.pow(1024, i)).toFixed(2) + ' ' + ['B', 'kB', 'MB', 'GB', 'TB'][i];
    }

    const downloadItem = (objectName: string) => {
        fetch(`item/${objectName}/download`).then(response => response.blob()).then((res) => {
            const aElement = document.createElement("a");
            aElement.setAttribute("download", objectName);
            const href = URL.createObjectURL(res);
            aElement.href = href;
            aElement.setAttribute("target", "_blank");
            aElement.click();
            URL.revokeObjectURL(href);
            setIsDownloadModalOpen(false);
        });
    }

    return (
        <List>
            {items?.map((item, index) => (
                <React.Fragment key={item.objectName}>
                    <ListItem sx={{borderBottom: '1px solid', marginBottom: '1px'}} button onClick={() => handleClick(index)}>
                        <ListItemText primary={item.objectName} />
                        {openIndex === index ? <ExpandLess /> : <ExpandMore />}
                    </ListItem>
                    <Collapse sx={{border: '1px solid', marginBottom: '1px'}} in={openIndex === index} timeout="auto" unmountOnExit>
                        <Typography variant="body2" style={{ marginLeft: '20px' }}>
                            Name: {item.objectName}
                        </Typography>
                        <Typography variant="body2" style={{ marginLeft: '20px' }}>
                            LastModified: {item.lastModified}
                        </Typography>
                        <Typography variant="body2" style={{ marginLeft: '20px' }}>
                            Size: {humanReadableSize(item.size)}
                        </Typography>
                        <Button style={{marginTop: '20px', marginLeft: '20px'}} variant="outlined"
                            onClick={() => setIsDownloadModalOpen(true)}
                        >
                            Download
                        </Button>
                        <Dialog
                            open={isDownloadModalOpen}
                            onClose={() => setIsDownloadModalOpen(false)}
                            aria-labelledby="alert-dialog-title"
                            aria-describedby="alert-dialog-description"
                        >
                            <DialogTitle id="alert-dialog-title">
                                Do you want to download file {item.objectName}?
                            </DialogTitle>
                            <DialogActions>
                                <Button variant="contained" onClick={() => downloadItem(item.objectName)} autoFocus>Download</Button>
                                <Button onClick={() => setIsDownloadModalOpen(false)}>
                                    Cancel
                                </Button>
                            </DialogActions>
                        </Dialog>
                    </Collapse>
                </React.Fragment>
            ))}
        </List>
    );
};

export default BucketItemsList;