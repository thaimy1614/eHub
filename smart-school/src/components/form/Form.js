import React from "react";
import {
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
    Button,
    FormControl,
    FormLabel,
    Input,
    Select,
    RadioGroup,
    Radio,
    Stack,
    useToast,
    FormErrorMessage,
} from "@chakra-ui/react";
import { Controller, useForm } from "react-hook-form";
import axios from "axios";

const FormModal = ({ title, fields, isOpen, onClose, onSubmit }) => {
    const toast = useToast();
    const {
        handleSubmit,
        register,
        setValue,
        control,
        formState: { errors, isSubmitting },
        reset,
    } = useForm();

    const handleImageUpload = async (e) => {
        const file = e.target.files[0];
        if (!file) return;

        const formData = new FormData();
        formData.append("file", file);

        try {
            // Here to call api update image
            //   const response = await axios.post(
            //     "",
            //     formData
            //   );
            setValue("image", "https://abc.com/image.png");
        } catch (error) {
            console.error("Upload failed", error);
            toast({
                title: "Upload failed",
                status: "error",
                duration: 2000,
                isClosable: true,
                position: "top-right",
            });
        }
    };

    const onSubmitForm = async (data) => {
        try {
            await onSubmit(data);
            toast({
                title: "User added successfully",
                status: "success",
                duration: 3000,
                isClosable: true,
                position: "top-right",
            });
            reset();
            onClose();
        } catch (error) {
            toast({
                title: "Error",
                description: "Could not add user. Please try again.",
                status: "error",
                duration: 3000,
                isClosable: true,
                position: "top-right",
            });
        }
    };

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <ModalOverlay />
            <ModalContent>
                <form onSubmit={handleSubmit(onSubmitForm)}>
                    <ModalHeader>{title}</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        {fields.map((field) => (
                            <FormControl
                                key={field.name}
                                mb={4}
                                isInvalid={errors[field.name]}
                            >
                                <FormLabel>{field.label}</FormLabel>
                                {field.type === "select" ? (
                                    <Select
                                        {...register(field.name, {
                                            required: "This field is required",
                                        })}
                                    >
                                        {field.options.map((option) => (
                                            <option key={option.value} value={option.value}>
                                                {option.label}
                                            </option>
                                        ))}
                                    </Select>
                                ) : field.type === "radio" ? (
                                    <Controller
                                        name={field.name}
                                        control={control}
                                        rules={{ required: "This field is required" }}
                                        render={({ field: selectField }) => (<Select {...selectField}>
                                            {field.options.map((option) => (
                                                <option key={option.value} value={option.value}>
                                                    {option.label}
                                                </option>
                                            ))}
                                        </Select>
                                        )}
                                    />
                                ) : field.type === "file" ? (
                                    <Input
                                        type="file"
                                        accept="image/*"
                                        onChange={handleImageUpload}
                                    />
                                ) : (
                                    <Input
                                        type={field.type || "text"}
                                        placeholder={field.placeholder || ""}
                                        {...register(field.name, {
                                            required: "This field is required",
                                            pattern:
                                                field.name === "email"
                                                    ? {
                                                        value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                                                        message: "Invalid email address",
                                                    }
                                                    : field.name === "phoneNumber"
                                                        ? {
                                                            value: /^0\d{9}$/,
                                                            message:
                                                                "Phone number must start with 0 and have 10 digits",
                                                        }
                                                        : undefined,
                                        })}
                                    />
                                )}
                                <FormErrorMessage>
                                    {errors[field.name] && errors[field.name].message}
                                </FormErrorMessage>
                            </FormControl>
                        ))}
                    </ModalBody>
                    <ModalFooter>
                        <Button
                            colorScheme="blue"
                            mr={3}
                            type="submit"
                            isLoading={isSubmitting}
                        >
                            Add User
                        </Button>
                        <Button onClick={onClose}>Cancel</Button>
                    </ModalFooter>
                </form>
            </ModalContent>
        </Modal>
    );
};
export default FormModal;